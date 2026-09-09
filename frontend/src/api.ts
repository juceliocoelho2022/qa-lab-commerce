import type { Order, Product } from './types'

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api'

async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
  const response = await fetch(`${API_URL}${path}`, {
    ...options,
    headers: { 'Content-Type': 'application/json', ...options.headers }
  })
  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: 'Erro inesperado' }))
    throw new Error(error.message ?? `HTTP ${response.status}`)
  }
  return response.json()
}

export const api = {
  login: (email: string, password: string) => request<{ accessToken: string; role: string }>('/auth/login', {
    method: 'POST', body: JSON.stringify({ email, password })
  }),
  products: () => request<Product[]>('/products'),
  createOrder: (token: string, productId: number) => request<Order>('/orders', {
    method: 'POST', headers: { Authorization: `Bearer ${token}` },
    body: JSON.stringify({ items: [{ productId, quantity: 1 }] })
  }),
  pay: (token: string, orderId: number, cardNumber: string) => request<Order>(`/orders/${orderId}/payment`, {
    method: 'POST', headers: { Authorization: `Bearer ${token}` }, body: JSON.stringify({ cardNumber })
  })
}
