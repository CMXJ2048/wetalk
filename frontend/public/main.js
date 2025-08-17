const form = document.getElementById('login-form')
const hint = document.getElementById('hint')
const accountEl = document.getElementById('account')
const passEl = document.getElementById('password')
const btnRegister = document.getElementById('btn-register')
const brandSub = document.getElementById('brand-sub')
const formRegister = document.getElementById('register-form')
const hintRegister = document.getElementById('hint-register')
const btnBackLogin = document.getElementById('btn-back-login')
const rAccount = document.getElementById('r-account')
const rEmail = document.getElementById('r-email')
const rDisplayName = document.getElementById('r-displayName')
const rPassword = document.getElementById('r-password')
const rPassword2 = document.getElementById('r-password2')
const strengthEl = document.getElementById('strength')
const btnRegisterSubmit = document.getElementById('btn-register-submit')
const canvas = document.getElementById('bg-canvas')
const ctx = canvas?.getContext('2d')

function setHint(msg, cls = '') {
  hint.textContent = msg || ''
  hint.className = `hint ${cls}`.trim()
}
function setHintR(msg, cls = '') {
  hintRegister.textContent = msg || ''
  hintRegister.className = `hint ${cls}`.trim()
}

function setValid(el, ok) {
  const wrap = el.closest('.input-wrap')
  if (!wrap) return
  if (ok) {
    el.classList.remove('invalid')
    wrap.classList.add('valid')
  } else {
    wrap.classList.remove('valid')
  }
}

function passwordStrength(pwd) {
  let s = 0
  if (pwd.length >= 8) s++
  if (/[A-Z]/.test(pwd)) s++
  if (/[a-z]/.test(pwd)) s++
  if (/[0-9]/.test(pwd)) s++
  if (/[^A-Za-z0-9]/.test(pwd)) s++
  if (pwd.length >= 12) s++
  if (s <= 2) return 'weak'
  if (s <= 4) return 'medium'
  return 'strong'
}

function renderStrength(pwd) {
  if (!strengthEl) return
  const level = passwordStrength(pwd)
  strengthEl.textContent = level === 'weak' ? '密码强度：弱' : level === 'medium' ? '密码强度：中' : '密码强度：强'
  strengthEl.className = `strength ${level}`
}

function validateAll() {
  const okAccount = !!rAccount.value.trim()
  const okEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(rEmail.value.trim())
  const pwd = rPassword.value
  const okPwd = pwd.length >= 8
  const okPwd2 = rPassword2.value && rPassword2.value === pwd
  setValid(rAccount, okAccount)
  setValid(rEmail, okEmail)
  setValid(rPassword, okPwd)
  setValid(rPassword2, okPwd2)
  btnRegisterSubmit.disabled = !(okAccount && okEmail && okPwd && okPwd2 && rDisplayName.value.trim())
}

form?.addEventListener('submit', async (e) => {
  e.preventDefault()
  const account = accountEl.value.trim()
  const password = passEl.value
  if (!account || !password) {
    setHint('请输入用户名和密码', 'error')
    return
  }
  setHint('登录中…')
  try {
    const res = await fetch('/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ account, password })
    })
    const data = await res.json().catch(() => ({}))
    if (!res.ok || !data.token) {
      setHint(data.error || '登录失败', 'error')
      return
    }
    // save token
    localStorage.setItem('token', data.token)
    localStorage.setItem('account', data.account || account)
    localStorage.setItem('userId', String(data.userId || ''))
    setHint('登录成功', 'success')
    // Redirect placeholder: navigate to a home page when available
    // location.href = '/home.html'
  } catch (err) {
    setHint('网络错误，请稍后再试', 'error')
  }
})

btnRegister?.addEventListener('click', () => {
  // Switch to register form
  form.style.display = 'none'
  formRegister.style.display = ''
  brandSub.textContent = '欢迎注册'
  setHint('')
  setHintR('请填写账户名、邮箱、昵称与密码')
})

btnBackLogin?.addEventListener('click', () => {
  // Back to login form
  formRegister.style.display = 'none'
  form.style.display = ''
  brandSub.textContent = '欢迎登录'
  setHintR('')
  setHint('')
})

formRegister?.addEventListener('submit', async (e) => {
  e.preventDefault()
  const account = rAccount.value.trim()
  const email = rEmail.value.trim()
  const displayName = rDisplayName.value.trim()
  const password = rPassword.value
  if (!account || !email || !displayName || !password) {
    setHintR('请完整填写信息', 'error')
    return
  }
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
    setHintR('邮箱格式不正确', 'error')
    rEmail.classList.add('invalid')
    rEmail.focus()
    return
  }
  setHintR('注册中…')
  try {
    const res = await fetch('/api/users', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ account, email, displayName, password })
    })
    const data = await res.json().catch(() => ({}))
    if (!res.ok || !data.id) {
      setHintR(data.error || '注册失败', 'error')
      return
    }
    setHintR('注册成功，请登录', 'success')
    // Switch back to login quickly
    setTimeout(() => {
      formRegister.style.display = 'none'
      form.style.display = ''
      brandSub.textContent = '欢迎登录'
      setHint('请使用新账户登录', 'success')
    }, 600)
  } catch (err) {
    setHintR('网络错误，请稍后再试', 'error')
  }
})

// 实时校验邮箱格式
rEmail?.addEventListener('input', () => {
  const v = rEmail.value.trim()
  if (!v) { rEmail.classList.remove('invalid'); setHintR(''); return }
  const ok = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v)
  if (ok) {
    rEmail.classList.remove('invalid')
    setHintR('邮箱格式正确')
  } else {
    rEmail.classList.add('invalid')
    setHintR('邮箱格式不正确', 'error')
  }
  setValid(rEmail, ok)
  validateAll()
})

rAccount?.addEventListener('input', () => { setValid(rAccount, !!rAccount.value.trim()); validateAll() })
rDisplayName?.addEventListener('input', () => { setValid(rDisplayName, !!rDisplayName.value.trim()); validateAll() })
rPassword?.addEventListener('input', () => { renderStrength(rPassword.value); setValid(rPassword, rPassword.value.length >= 8); validateAll() })
rPassword2?.addEventListener('input', () => { setValid(rPassword2, !!rPassword2.value && rPassword2.value === rPassword.value); validateAll() })

// ============ Background Falling Balls ============
;(function fallingBalls() {
  if (!canvas || !ctx) return
  const DPR = Math.min(window.devicePixelRatio || 1, 2)
  function resize() {
    canvas.width = Math.floor(window.innerWidth * DPR)
    canvas.height = Math.floor(window.innerHeight * DPR)
  }
  resize()
  window.addEventListener('resize', resize)

  const colors = [
    '#ff6b6b', // red
    '#ffd93d', // yellow
    '#6bcBef', // light blue
    '#51cf66', // green
    '#b197fc', // purple
    '#ffa94d'  // orange
  ]

  const SPAWN_RATE = 14 // balls per second
  const MAX_BALLS = 140
  const balls = []

  function spawn(n) {
    for (let i = 0; i < n; i++) {
      if (balls.length >= MAX_BALLS) break
      const w = canvas.width
      const r = (2 + Math.random() * 5) * DPR
      balls.push({
        x: Math.random() * w,
        y: -r * 2,
        vx: (Math.random() - 0.5) * 30 * DPR,   // slight drift
        vy: (60 + Math.random() * 160) * DPR,   // falling speed
        r,
        color: colors[(Math.random() * colors.length) | 0],
        glow: 6 + Math.random() * 10
      })
    }
  }

  let last = performance.now()
  let acc = 0

  function tick(now = performance.now()) {
    const dt = Math.min(0.033, (now - last) / 1000)
    last = now
    acc += dt * SPAWN_RATE
    const n = acc | 0
    if (n > 0) {
      spawn(n)
      acc -= n
    }

    // Fade existing drawings toward transparency to create trails, without painting over CSS gradient
    ctx.globalCompositeOperation = 'destination-out'
    ctx.fillStyle = 'rgba(0,0,0,0.08)'
    ctx.fillRect(0, 0, canvas.width, canvas.height)

    ctx.globalCompositeOperation = 'lighter' // additive glow for vibrant colors

    for (let i = balls.length - 1; i >= 0; i--) {
      const b = balls[i]
      b.x += b.vx * dt
      b.y += b.vy * dt
      if (b.y - b.r > canvas.height) {
        balls.splice(i, 1)
        continue
      }
      ctx.shadowBlur = b.glow
      ctx.shadowColor = b.color
      ctx.fillStyle = b.color
      ctx.beginPath()
      ctx.arc(b.x, b.y, b.r, 0, Math.PI * 2)
      ctx.fill()
    }

    ctx.shadowBlur = 0
    ctx.globalCompositeOperation = 'source-over'
    requestAnimationFrame(tick)
  }

  // kick off
  requestAnimationFrame(tick)
})()
