'use_strict';

import * as mongoose from 'mongoose';
import { IMovie, IMovieWithId } from '../interfaces/movies';

export interface MovieEntity extends IMovieWithId, mongoose.Document { }

let MovieSchema = new mongoose.Schema({
    id: Number,
    title: String,
    year: Number,
    director: String,
    actor: [String]
});

export var Movie = mongoose.model<MovieEntity>('Movie', MovieSchema, 'Movies');
