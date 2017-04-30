'use strict';

import * as express from 'express';
import { Movie } from '../schemas/movies';
import { IMovie, IMovieId, IMovieList, IMovieIdList, IMovieWithId } from '../interfaces/movies';
import { generateMovieId } from '../utils/id_generator_service';

const router = express.Router();

// GET /movies/find?year={year}&orderby={field}
router.get('/find', (req, res) => {
    const year: number = parseInt(req.query.year, 10);
    const orderBy: string = req.query.orderby;

    if (orderBy === 'Title' || orderBy === 'Director') {
        const sort = orderBy === 'Title' ? { title: 1 } : { director: 1 };
        Movie.find({ year: year }, { _id: 0, __v: 0 }, { sort: orderBy.toLowerCase() }, (err, movies) => {
            if (err) {
                res.status(500).json({info: 'Error executing query.', error: err});
            };
            if (movies) {
                const movieIdList: IMovieIdList = {
                    id: movies.map(m => m.id)
                };
                res.json(movieIdList);
            } else {
                res.sendStatus(400);
            }
        });
    }
});

// GET /movies
router.get('/', (req, res) => {
    Movie.find({}, { id: 0, _id: 0, __v: 0 }, (err, movies) => {
        if (err) {
            res.status(500).json({info: 'Error executing query.', error: err});
        };
        if (movies) {
            const movieList: IMovieList = {
                movie: movies.map(m => {
                    const movieResult: IMovie = {
                        title: m.title,
                        year: m.year,
                        director: m.director,
                        actor: m.actor
                    };
                    return movieResult;
                })
            };
            res.json(movieList);
        }
    });
});

// GET /movies/{id}
router.get('/:id', (req, res) => {
    Movie.findOne({ id: req.params.id }, { id: 0, _id: 0, __v: 0 }, (err, movie) => {
        if (err) {
            res.status(500).json({info: 'Error executing query.', error: err});
        };
        if (movie) {
            const movieResult: IMovie = {
                title: movie.title,
                year: movie.year,
                director: movie.director,
                actor: movie.actor
            };
            res.json(movieResult);
        } else {
            res.status(404).end();
        }
    });
});

// POST /movies
router.post('/', (req, res) => {
    const movie = <IMovieWithId> req.body;
    Movie.find({}, { id: 1 },  (err, movies) => {
        movie.id = generateMovieId(movies);
        Movie.insertMany([movie], (err, result) => {
            if (err) {
                res.sendStatus(500);
            };
            const resResult: IMovieId = { id: result[0].id };
            res.json(resResult);
        });
    });
});

// PUT /movies/{id}
router.put('/:id', (req, res) => {
    const movie = <IMovieWithId> req.body;
    Movie.findOneAndUpdate({ id: req.params.id }, movie, { upsert: true }, (err, result) => {
        if (err) {
            res.sendStatus(500);
        };
        res.status(204).end();
    });
});

// DELETE /movies/{id}
router.delete('/:id', (req, res) => {
    const id = req.params.id;
    Movie.remove({ id: req.params.id }, err => {
        if (err) {
            res.sendStatus(500);
        };
        res.status(204).end();
    });
});

export default router;
