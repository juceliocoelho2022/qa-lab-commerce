import type { Product } from '../types'

type Props = { product: Product; onBuy: (product: Product) => void; disabled: boolean }

export function ProductCard({ product, onBuy, disabled }: Props) {
  return <article className="product-card" data-testid={`product-${product.sku}`}>
    <span className="sku">{product.sku}</span>
    <h3>{product.name}</h3>
    <strong>{product.price.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</strong>
    <small>{product.stock > 0 ? `${product.stock} em estoque` : 'Sem estoque'}</small>
    <button onClick={() => onBuy(product)} disabled={disabled || product.stock === 0}>Comprar e testar</button>
  </article>
}
