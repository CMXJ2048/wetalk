async function loadUsers() {
  const status = document.getElementById('status')
  const ul = document.getElementById('users')
  try {
    const res = await fetch('/api/users')
    if (!res.ok) throw new Error('HTTP ' + res.status)
    const data = await res.json()
    status.textContent = `Loaded ${data.length} users`
    ul.innerHTML = data.map(u => `<li>${u.username} (${u.email || ''})</li>`).join('')
  } catch (err) {
    status.textContent = 'Failed to load users'
  }
}

loadUsers()
