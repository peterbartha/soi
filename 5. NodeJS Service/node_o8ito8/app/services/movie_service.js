'use strict';
Object.defineProperty(exports, "__esModule", { value: true });
const express = require("express");
const router = express.Router();
router.get('/', (req, res) => {
    let name = req.query.name;
    res.send("Hello: " + name);
});
exports.default = router;
//# sourceMappingURL=movie_service.js.map