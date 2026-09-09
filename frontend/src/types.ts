export type Product = { id: number; name: string; sku: string; price: number; stock: number; active: boolean }
export type Order = { id: number; status: 'CREATED' | 'PAID' | 'DECLINED'; total: number }
