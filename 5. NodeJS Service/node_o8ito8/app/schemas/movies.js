'use_strict';
"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose = require("mongoose");
let MovieSchema = new mongoose.Schema({
    id: Number,
    title: String,
    year: Number,
    director: String,
    actor: [String]
});
exports.Movie = mongoose.model('Movie', MovieSchema, 'Movies');
//# sourceMappingURL=movies.js.map