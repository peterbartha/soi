'use strict';

import * as express from 'express';
import moviesService from '../services/movies_service';

const router = express.Router();

router.use('/movies', moviesService);

/* GET home page. */
router.get('/',(req,res,next) => {
  res.render('index', {title: 'Express'});
});

export default router;