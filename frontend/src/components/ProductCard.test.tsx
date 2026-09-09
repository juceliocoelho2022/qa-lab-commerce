import { fireEvent, render, screen } from '@testing-library/react'
import { describe, expect, it, vi } from 'vitest'
import { ProductCard } from './ProductCard'

describe('ProductCard', () => {
  const product = { id: 1, name: 'Mouse QA', sku: 'MOUSE-001', price: 99.9, stock: 3, active: true }

  it('shows product and sends buy action', () => {
    const onBuy = vi.fn()
    render(<ProductCard product={product} onBuy={onBuy} disabled={false} />)
    fireEvent.click(screen.getByRole('button', { name: /comprar/i }))
    expect(screen.getByText('Mouse QA')).toBeInTheDocument()
    expect(onBuy).toHaveBeenCalledWith(product)
  })

  it('disables purchase when stock is zero', () => {
    render(<ProductCard product={{ ...product, stock: 0 }} onBuy={vi.fn()} disabled={false} />)
    expect(screen.getByRole('button', { name: /comprar/i })).toBeDisabled()
  })
})
