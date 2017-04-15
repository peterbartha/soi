package program;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import movies.MovieDatabase;
import providers.ProtoBufProvider;

public class Program {
	public static void main(String[] args) {
		try {
			// Instantiate the container
			Swarm swarm = new Swarm(args);
			// Create one or more deployments
			JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
			// Add resources to deployment
			deployment.addPackage(MovieDatabase.class.getPackage());
			deployment.addPackage(ProtoBufProvider.class.getPackage());
			deployment.addAllDependencies();
			// Start the swarm microserver:
			swarm.start();
			swarm.deploy(deployment);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}