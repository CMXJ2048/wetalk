// Simple Node.js server: serve static HTML and proxy /api to Spring Boot
import express from 'express'
import { createProxyMiddleware } from 'http-proxy-middleware'
import path from 'node:path'
import { fileURLToPath } from 'node:url'

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)

const app = express()
const PORT = process.env.PORT || 3000

// Static files
app.use(express.static(path.join(__dirname, 'public')))

// API proxy to backend
app.use('/api', createProxyMiddleware({
  target: 'http://localhost:8080',
  changeOrigin: true,
}))

app.listen(PORT, () => {
  console.log(`Web server running at http://localhost:${PORT}`)
})
