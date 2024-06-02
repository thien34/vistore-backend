import { nomarlUrl } from './helper'

class HttpError extends Error {
    constructor(status, message) {
        super(message)
        this.status = status
    }
}

const httpRequest = async (url, method = 'GET', body = null, config = {}) => {
    try {
        const fullUrl = `${import.meta.env.VITE_BACKEND_URL}${nomarlUrl(url)}`
        const res = await fetch(fullUrl, {
            method,
            body: JSON.stringify(body),
            credentials: 'include',
            ...config,
            headers: {
                'Content-Type': 'application/json',
                ...config?.headers
            }
        })
        const payload = await res.json()
        const data = {
            status: res.status,
            payload
        }
        return data
    } catch (error) {
        if (error instanceof HttpError) {
            throw new HttpError(error.status, error.message)
        }
        throw error
    }
}

const http = {
    get(url, config = {}) {
        return httpRequest(url, 'GET', null, config)
    },
    post(url, body, config = {}) {
        return httpRequest(url, 'POST', body, config)
    },
    postForm(url, body, config = {}) {
        return httpRequest(url, 'POST', body, {
            ...config,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
    },
    put(url, body, config = {}) {
        return httpRequest(url, 'PUT', body, config)
    },
    patch(url, body, config = {}) {
        return httpRequest(url, 'PATCH', body, config)
    },
    delete(url, config = {}) {
        return httpRequest(url, 'DELETE', null, config)
    }
}

export { http }

export default httpRequest
