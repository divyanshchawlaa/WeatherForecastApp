const pool = require('../config');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const register = async (req, res) => {
  const { name, email, password } = req.body;

  try {
    const domain = email.split('@')[1];
    const uniResult = await pool.query(
      'SELECT * FROM universities WHERE email_domain = $1',
      [domain]
    );

    if (uniResult.rows.length === 0) {
      return res.status(400).json({ message: 'Invalid university email' });
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const result = await pool.query(
      'INSERT INTO users (name, email, password_hash, university_id) VALUES ($1, $2, $3, $4) RETURNING *',
      [name, email, hashedPassword, uniResult.rows[0].id]
    );

    const token = jwt.sign(
      { userId: result.rows[0].id, email, universityId: uniResult.rows[0].id },
      process.env.JWT_SECRET,
      { expiresIn: '1h' }
    );

    res.status(201).json({ message: 'User registered successfully', token, user: result.rows[0] });

  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error' });
  }
};

const login = async (req, res) => {
  const { email, password } = req.body;

  try {
    const userResult = await pool.query('SELECT * FROM users WHERE email = $1', [email]);
    const user = userResult.rows[0];
    if (!user) return res.status(400).json({ message: 'Invalid credentials' });

    const validPass = await bcrypt.compare(password, user.password_hash);
    if (!validPass) return res.status(400).json({ message: 'Invalid credentials' });

    const token = jwt.sign(
      { userId: user.id, email, universityId: user.university_id },
      process.env.JWT_SECRET,
      { expiresIn: '1h' }
    );

    res.json({ message: 'Logged in successfully', token, user });

  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Server error' });
  }
};

module.exports = { register, login };
