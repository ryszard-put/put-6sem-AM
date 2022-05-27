const express = require('express');
const { mockRoutes } = require('./mockRoutes');

const PORT = 4000;
const app = express();

app.get('/', (_, res) => res.json({ message: 'api-working' }));

app.get('/jogging', (_, res) => {
  console.log('test');

  res.json(mockRoutes);
});

app.listen(PORT, () => console.log(`listening on port ${PORT}`));
