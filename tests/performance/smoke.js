import http from 'k6/http'
import { check, sleep } from 'k6'

const baseUrl = __ENV.BASE_URL || 'http://localhost:8080'

export const options = {
  vus: 3,
  duration: '20s',
  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<500']
  }
}

export default function () {
  const response = http.get(`${baseUrl}/api/products`)
  check(response, {
    'status is 200': r => r.status === 200,
    'catalog is not empty': r => Array.isArray(r.json()) && r.json().length > 0
  })
  sleep(1)
}
