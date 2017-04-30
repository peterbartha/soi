'use strict';
Object.defineProperty(exports, "__esModule", { value: true });
const express = require("express");
const movies_service_1 = require("../services/movies_service");
const router = express.Router();
router.use('/movies', movies_service_1.default);
router.get('/', (req, res, next) => {
    res.render('index', { title: 'Express' });
});
exports.default = router;
//# sourceMappingURL=index.js.map