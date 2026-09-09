import { FormEvent, useEffect, useState } from 'react'
import { api } from './api'
import { ProductCard } from './components/ProductCard'
import type { Order, Product } from './types'

export default function App() {
  const [email, setEmail] = useState('qa.user@qalab.dev')
  const [password, setPassword] = useState('user123')
  const [token, setToken] = useState('')
  const [products, setProducts] = useState<Product[]>([])
  const [order, setOrder] = useState<Order | null>(null)
  const [message, setMessage] = useState('')

  useEffect(() => { api.products().then(setProducts).catch(e => setMessage(e.message)) }, [])

  async function login(event: FormEvent) {
    event.preventDefault()
    try {
      const data = await api.login(email, password)
      setToken(data.accessToken)
      setMessage(`Login realizado — perfil ${data.role}`)
    } catch (error) { setMessage((error as Error).message) }
  }

  async function buy(product: Product) {
    try {
      const created = await api.createOrder(token, product.id)
      setOrder(created)
      setMessage(`Pedido #${created.id} criado com sucesso`)
    } catch (error) { setMessage((error as Error).message) }
  }

  async function pay(cardNumber: string) {
    if (!order) return
    try {
      const paid = await api.pay(token, order.id, cardNumber)
      setOrder(paid)
      setMessage(`Pagamento processado: ${paid.status}`)
    } catch (error) { setMessage((error as Error).message) }
  }

  return <main>
    <header><span className="badge">PORTFÓLIO QA</span><h1>QA Lab Commerce</h1><p>Um sistema real para praticar testes manuais e automatizados.</p></header>
    <section className="panel login-panel">
      <div><h2>1. Autenticação</h2><p>Teste credenciais válidas, inválidas e autorização por perfil.</p></div>
      <form onSubmit={login}>
        <label>E-mail<input aria-label="E-mail" value={email} onChange={e => setEmail(e.target.value)} /></label>
        <label>Senha<input aria-label="Senha" type="password" value={password} onChange={e => setPassword(e.target.value)} /></label>
        <button type="submit">Entrar</button>
      </form>
    </section>
    {message && <div role="status" className="status">{message}</div>}
    <section><h2>2. Catálogo e estoque</h2><div className="grid">
      {products.map(product => <ProductCard key={product.id} product={product} onBuy={buy} disabled={!token} />)}
    </div></section>
    {order && <section className="panel order-panel">
      <div><h2>3. Pagamento do pedido #{order.id}</h2><p>Total: R$ {order.total.toFixed(2)} · Status: <strong>{order.status}</strong></p></div>
      {order.status === 'CREATED' && <div className="payment-actions">
        <button onClick={() => pay('4111111111111111')}>Aprovar pagamento</button>
        <button className="secondary" onClick={() => pay('4111111111111110')}>Simular recusa</button>
      </div>}
    </section>}
  </main>
}
