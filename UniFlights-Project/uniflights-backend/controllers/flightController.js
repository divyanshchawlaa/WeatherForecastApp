const flights = [
  { airline: 'Emirates', price: 500, duration: '8h', origin: 'DEL', destination: 'BOM' },
  { airline: 'Air India', price: 450, duration: '9h', origin: 'DEL', destination: 'BOM' },
  { airline: 'IndiGo', price: 400, duration: '9h 30m', origin: 'DEL', destination: 'BOM' },
  { airline: 'Singapore Airlines', price: 700, duration: '7h 45m', origin: 'DEL', destination: 'BOM' },
];

const searchFlights = (req, res) => {
  const { origin, destination, date, student } = req.query;

  let results = flights.filter(f =>
    f.origin === origin.toUpperCase() && f.destination === destination.toUpperCase()
  );

  if (student === 'true') {
    results = results.map(f => ({ ...f, price: Math.round(f.price * 0.85) }));
  }

  results = results.map(f => ({
    ...f,
    book_link: `https://www.google.com/flights?q=${origin}+${destination}+${date}`
  }));

  res.json(results);
};

module.exports = { searchFlights };
