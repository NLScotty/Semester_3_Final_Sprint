const bcrypt = require('bcrypt')
const LocalStrategy = require("passport-local").Strategy

function initialize(passport, getUserByEmail, getUserById){
    const authenticateUser = async (email, password, done) =>{
        const user = await getUserByEmail(email)
        console.log(user)
        if(user == null){
            return done(null, false, {message: 'Invalid login, please try again!'})
        }

        try{
            if(await bcrypt.compare(password, user.password)){
                return done(null, user)
            }else{
                return done(null, false, {message: 'Invalid login, please try again!'})
            }
        } catch (e) {
            return done(e)
        }
    }
    passport.use(new LocalStrategy({usernameField: 'email'}, authenticateUser))
    passport.serializeUser((user, done) => done(null, user.id))
    passport.deserializeUser(async (id, done) => {
        const user = await getUserById(id);
        done(null, user);
    })
}

module.exports = initialize