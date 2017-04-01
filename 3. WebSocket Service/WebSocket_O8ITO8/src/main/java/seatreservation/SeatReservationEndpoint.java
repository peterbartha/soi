package seatreservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import websocket.message.WebSocketMessage;
import websocket.message.WebSocketMessageEncoder;
import websocket.message.WebSocketMessageDecoder;

@ServerEndpoint(
	value = "/cinema",
	decoders = { WebSocketMessageDecoder.class},
	encoders = { WebSocketMessageEncoder.class}
)
public class SeatReservationEndpoint {
	
	private static boolean initialized;
	
	private static Set<Session> connections;
	private static WebSocketMessageEncoder messageEncoder;
	
	private static List<Seat> seats;
	private static List<LockResult> locks;
	
	private static int rows;
	private static int columns;
	
	
	public SeatReservationEndpoint() {
		messageEncoder = new WebSocketMessageEncoder();
		seats = new ArrayList<Seat>();
		locks = new ArrayList<LockResult>();
		rows = 0;
		columns = 0;
		
		if (connections == null) {
			connections = Collections.synchronizedSet(new HashSet<Session>());
		}
	}
	
	@OnOpen
	public void open(Session session) {
		System.out.println("WebSocket opened: " + session.getId());
		connections.add(session);
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("WebSocket closed: " + session.getId());
		connections.remove(session);
	}
	
	@OnError
	public void error(Throwable throwable) {
		System.out.println("WebSocket error: " + throwable.getMessage());
	}
	
	@OnMessage
	public void message(WebSocketMessage message, Session session) {
		System.out.println("WebSocket message: " + message);
		
		switch (message.getType()) {
			case "initRoom":
				this.initializeRoom(message, session);
				break;
				
			case "getRoomSize": 
				this.sendRoomSize(session);
				break;
				
			case "updateSeats":
				this.updateSeats(session);
				break;
				
			case "lockSeat": 
				this.lockSeat(message, session);
				break;
				
			case "unlockSeat":
				this.unlockSeat(message, session, SeatStatus.FREE);
				break;
				
			case "reserveSeat":
				this.reserveSeat(message, session, SeatStatus.RESERVED);
				break;
				
			default: 
				System.out.println("Unhandled message type: " + message.getType());
				break;
		}
	}
	
	private void initializeRoom(WebSocketMessage message, Session session) {
		Map<String, Object> properties = message.getProperties();
		if (properties.containsKey("rows") && properties.containsKey("columns")) {
			int newRows = new Integer(properties.get("rows").toString());
			int newColumns = new Integer(properties.get("columns").toString());
			
			System.out.println("initRoom with " + newRows + " rows and " + newColumns + " columns.");
			if (newRows <= 0 || newColumns <= 0) {
				String errorMessage = "Number of rows and columns must be integer and greater than 0.";
				this.sendErrorMessage(errorMessage, session);
				return;
			}
			
			rows = newRows;
			columns = newColumns;
			seats = new ArrayList<Seat>();
			locks = new ArrayList<LockResult>();
			initialized = true;
			
			for (int i = 1; i < rows + 1; i++) {
				for (int j = 1; j < columns + 1; j++) {
					seats.add(new Seat(i, j));
				}
			}
			
			WebSocketMessage roomSizeMessage = new WebSocketMessage();
			
			roomSizeMessage.setType("roomSize");
			roomSizeMessage.addProperty("rows", rows);
			roomSizeMessage.addProperty("columns", columns);
			
			this.broadcastMessage(roomSizeMessage);
		} else {
			this.sendErrorMessage("initRoom message has to contain the following properties: \"rows\", \"columns\".", session);
		}
	}
	
	private void sendRoomSize(Session session) {
		System.out.println("getRoomSize");
		WebSocketMessage roomSizeMsg = new WebSocketMessage();
		
		roomSizeMsg.setType("roomSize");
		roomSizeMsg.addProperty("rows", rows);
		roomSizeMsg.addProperty("columns", columns);
		
		this.sendMessage(roomSizeMsg, session);
	}
	
	private void updateSeats(Session session) {
		System.out.println("updateSeats");
		for (Seat seat: seats) {
			WebSocketMessage seatStatusMessage = new WebSocketMessage();
			
			seatStatusMessage.setType("seatStatus");
			seatStatusMessage.addProperty("row", seat.getRow());
			seatStatusMessage.addProperty("column", seat.getColumn());
			seatStatusMessage.addProperty("status", seat.getStatus().toString().toLowerCase());
			
			this.sendMessage(seatStatusMessage, session);
		}
	}
	
	private void lockSeat(WebSocketMessage message, Session session) {
		if (!initialized) {
			String errorMessage = "Room is not initialized.";
			this.sendErrorMessage(errorMessage, session);
			return;
		}
		
		Map<String, Object> properties = message.getProperties();
		if (properties.containsKey("row") && properties.containsKey("column")) {
			int row = new Integer(properties.get("row").toString());
			int column = new Integer(properties.get("column").toString());
			System.out.println("lockSeat in row: " + row + ", column: " + column + ".");
			
			if (row <= 0 || column <= 0 || rows + 1 < row || columns + 1 < column) {
				String errorMessage = "Row number must be between 1 and " + rows + " and column between 1 and " + columns + ".";
				this.sendErrorMessage(errorMessage, session);
				return;
			}
			
			Seat seatToLock = getSeatAt(row, column);
			if (seatToLock == null) {
				String errorMessage = "Seat does not exist at row: " + row + ", column: " + column + ".";
				this.sendErrorMessage(errorMessage, session);
				return;
			}
			
			if (seatToLock.getStatus() != SeatStatus.FREE) {
				String errorMessage = "Seat is not free!";
				this.sendErrorMessage(errorMessage, session);
				return;
			}
			
			if (!setSeatStatus(seatToLock, SeatStatus.LOCKED)) {
				String errorMessage = "Failed to update seat status at row: " + seatToLock.getRow() + ", column: " + seatToLock.getColumn() + ".";
				this.sendErrorMessage(errorMessage, session);
				return;
			}
			
			LockResult lockResult = new LockResult(row, column);
			locks.add(lockResult);
			
			WebSocketMessage lockResultMessage = new WebSocketMessage();
			
			lockResultMessage.setType("lockResult");
			lockResultMessage.addProperty("lockId", lockResult.getId());
			
			this.sendMessage(lockResultMessage, session);
			this.broadcastSeatStatus(seatToLock);
		} else {
			this.sendErrorMessage("lockSeat message must contain the following properties: \"row\", \"column\".", session);
		}
		
	}
	
	private void unlockSeat(WebSocketMessage message, Session session, SeatStatus status) {
		if (!initialized) {
			this.sendErrorMessage("Room is not initialized.", session);
			return;
		}
		
		Map<String, Object> properties = message.getProperties();
		if (properties.containsKey("lockId")) {
			String lockId = properties.get("lockId").toString();
			lockId = lockId.substring(1, lockId.length() - 1);
			LockResult lock = getLock(lockId);
			
			if (lock == null) {
				String errorMessage = lockId + " lock does not exist.";
				this.sendErrorMessage(errorMessage, session);
				return;
			}
			
			Seat seat = getSeatAt(lock.getRow(), lock.getColumn());
			if (!setSeatStatus(seat, status)) {
				String errorMessage = "Failed to update seat status at row: " + seat.getRow() + ", column: " + seat.getColumn() + ".";
				this.sendErrorMessage(errorMessage, session);
				return;
			}
			
			locks.remove(lock);
			this.broadcastSeatStatus(seat);
		} else {
			this.sendErrorMessage("unlockSeat message must contain \"lockId\" property.", session);
		}
	}
	
	private void reserveSeat(WebSocketMessage message, Session session, SeatStatus status) {
		this.unlockSeat(message, session, status);
	}
	
	private boolean setSeatStatus(Seat seatToLock, SeatStatus status) {
		for (Seat seat: seats) {
			if (seat.equals(seatToLock)) {
				seatToLock.setStatus(status);
				
				int i = seats.indexOf(seat);
				seats.remove(i);
				seats.add(i, seatToLock);
				
				System.out.println("Changed seat status at row " + seatToLock.getRow() + ", column " + seatToLock.getColumn() + " to " + status.toString().toLowerCase() + ".");
				return true;
			}
		}
		
		System.out.println("Failed to update seat status at row: " + seatToLock.getRow() + ", column: " + seatToLock.getColumn() + ".");
		return false;
	}
	
	private static Seat getSeatAt(int row, int column) {
		for (Seat seat: seats) {
			if (seat.getRow() == row && seat.getColumn() == column) {
				return seat;
			}
		}
		return null;
	}
	
	private static LockResult getLock(String lockId) {
		for (LockResult lock: locks) {
			if (lock.getId().equals(lockId)) {
				return lock;
			}
		}
		return null;
	}
	
	private void broadcastMessage(WebSocketMessage message) {
		synchronized (connections) {
			System.out.println("Broadcasting " + message.getType() + " message to " + connections.size() + "user(s).");
			for (Session client : connections) {
				sendMessage(message, client);
			}
		}
	}
	
	private void broadcastSeatStatus(Seat seat) {
		WebSocketMessage seatStatusMessage = new WebSocketMessage();
		
		seatStatusMessage.setType("seatStatus");
		seatStatusMessage.addProperty("row", seat.getRow());
		seatStatusMessage.addProperty("column", seat.getColumn());
		seatStatusMessage.addProperty("status", seat.getStatus().toString().toLowerCase());
		
		broadcastMessage(seatStatusMessage);
	}
	
	private void sendMessage(WebSocketMessage message, Session session) {
		try {
			session.getBasicRemote().sendText(messageEncoder.encode(message));
		} catch (IOException e) {
			System.out.println("Could not send message to the following session: " + session.getId());
			e.printStackTrace();
		} catch (EncodeException e) {
			System.out.println("Failed to encode message.");
			e.printStackTrace();
		}
	}
	
	private void sendErrorMessage(String errMsg, Session session) {
		WebSocketMessage errorMessage = new WebSocketMessage();
		
		errorMessage.setType("error");
		errorMessage.addProperty("message", errMsg);
		
		sendMessage(errorMessage, session);
	}
	
}
