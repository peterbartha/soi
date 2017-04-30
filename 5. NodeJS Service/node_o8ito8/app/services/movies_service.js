'use strict';
Object.defineProperty(exports, "__esModule", { value: true });
const express = require("express");
const movies_1 = require("../schemas/movies");
const id_generator_service_1 = require("../utils/id_generator_service");
const router = express.Router();
router.get('/find', (req, res) => {
    const year = parseInt(req.query.year, 10);
    const orderBy = req.query.orderby;
    if (orderBy === 'Title' || orderBy === 'Director') {
        const sort = orderBy === 'Title' ? { title: 1 } : { director: 1 };
        movies_1.Movie.find({ year: year }, { _id: 0, __v: 0 }, { sort: orderBy.toLowerCase() }, (err, movies) => {
            if (err) {
                res.status(500).json({ info: 'Error executing query.', error: err });
            }
            ;
            if (movies) {
                const movieIdList = {
                    id: movies.map(m => m.id)
                };
                res.json(movieIdList);
            }
            else {
                res.sendStatus(400);
            }
        });
    }
});
router.get('/', (req, res) => {
    movies_1.Movie.find({}, { id: 0, _id: 0, __v: 0 }, (err, movies) => {
        if (err) {
            res.status(500).json({ info: 'Error executing query.', error: err });
        }
        ;
        if (movies) {
            const movieList = {
                movie: movies.map(m => {
                    const movieResult = {
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
router.get('/:id', (req, res) => {
    movies_1.Movie.findOne({ id: req.params.id }, { id: 0, _id: 0, __v: 0 }, (err, movie) => {
        if (err) {
            res.status(500).json({ info: 'Error executing query.', error: err });
        }
        ;
        if (movie) {
            const movieResult = {
                title: movie.title,
                year: movie.year,
                director: movie.director,
                actor: movie.actor
            };
            res.json(movieResult);
        }
        else {
            res.status(404).end();
        }
    });
});
router.post('/', (req, res) => {
    const movie = req.body;
    movies_1.Movie.find({}, { id: 1 }, (err, movies) => {
        movie.id = id_generator_service_1.generateMovieId(movies);
        movies_1.Movie.insertMany([movie], (err, result) => {
            if (err) {
                res.sendStatus(500);
            }
            ;
            const resResult = { id: result[0].id };
            res.json(resResult);
        });
    });
});
router.put('/:id', (req, res) => {
    const movie = req.body;
    movies_1.Movie.findOneAndUpdate({ id: req.params.id }, movie, { upsert: true }, (err, result) => {
        if (err) {
            res.sendStatus(500);
        }
        ;
        res.status(204).end();
    });
});
router.delete('/:id', (req, res) => {
    const id = req.params.id;
    movies_1.Movie.remove({ id: req.params.id }, err => {
        if (err) {
            res.sendStatus(500);
        }
        ;
        res.status(204).end();
    });
});
exports.default = router;
//# sourceMappingURL=movies_service.js.map