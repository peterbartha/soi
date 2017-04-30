"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
function generateMovieId(movies) {
    if (!movies.length)
        return 0;
    let id = 0;
    movies.forEach(movie => {
        const currentId = movie.id;
        if (currentId >= id) {
            id = currentId + 1;
        }
    });
    return id;
}
exports.generateMovieId = generateMovieId;
//# sourceMappingURL=id_generator_service.js.map