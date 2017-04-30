"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
function extractMovie() {
    const result = {
        title: this.title,
        year: this.year,
        director: this.director,
        actor: this.actor
    };
    return result;
}
exports.extractMovie = extractMovie;
//# sourceMappingURL=movie_extractor_service.js.map