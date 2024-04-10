const express =require('express');

const router = express.Router();

router.get('login', (request, response) => {
    response.render('login');    
})

router.get('register', (request, response) => {
    response.render('register'); 
})

app.post('/register', async (req, res) => {
    try {
      const hashedPassword = await bcrypt.hash(req.body.password, 10)
      /*
      users.push({
        id: Date.now().toString(),
        name: req.body.name,
        email: req.body.email,
        password: hashedPassword
      })
      */
      res.redirect('/login')
    } catch {
      res.redirect('/register')
    }
})

module.exports = router;