import { expect, test } from '@playwright/test'

test('customer logs in, creates an order and approves payment', async ({ page }) => {
  await page.goto('/')
  await expect(page.getByRole('heading', { name: 'QA Lab Commerce' })).toBeVisible()

  await page.getByLabel('E-mail').fill('qa.user@qalab.dev')
  await page.getByLabel('Senha').fill('user123')
  await page.getByRole('button', { name: 'Entrar' }).click()
  await expect(page.getByRole('status')).toContainText('Login realizado')

  await page.getByTestId('product-MOUSE-AUTO-002').getByRole('button').click()
  await expect(page.getByRole('status')).toContainText('criado com sucesso')

  await page.getByRole('button', { name: 'Aprovar pagamento' }).click()
  await expect(page.getByRole('status')).toContainText('PAID')
})

test('shows feedback for invalid credentials', async ({ page }) => {
  await page.goto('/')
  await page.getByLabel('E-mail').fill('qa.user@qalab.dev')
  await page.getByLabel('Senha').fill('senha-errada')
  await page.getByRole('button', { name: 'Entrar' }).click()
  await expect(page.getByRole('status')).toHaveText('Invalid email or password')
})
