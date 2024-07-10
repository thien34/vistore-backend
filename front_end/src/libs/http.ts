import { normalUrl } from './helper'

type Method = 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH' | 'HEAD'

class HttpError extends Error {
    status: number
    constructor({ status, message }: { status: number; message: string }) {
        super(message)
        this.status = status
    }
}

const httpRequest = async <Response>(url: string, method: Method = 'GET', body: unknown, config?: RequestInit) => {
    try {
        const fullUrl = `${import.meta.env.VITE_BACKEND_URL}${normalUrl(url)}`
        const options: RequestInit = {
            method,
            credentials: 'include',
            ...config,
            headers: {
                'Content-Type': 'application/json',
                ...config?.headers,
            },
        }

        const noBodyMethods: Method[] = ['GET', 'HEAD']
        if (!noBodyMethods.includes(method)) {
            options.body = JSON.stringify(body)
        }

        const res = await fetch(fullUrl, options)
        const payload: Response = await res.json()
        const data = {
            status: res.status,
            payload,
        }
        return data
    } catch (error) {
        if (error instanceof HttpError) {
            throw new HttpError({
                status: error.status,
                message: error.message,
            })
        }
        throw error
    }
}

const http = {
    get<Response>(url: string, config?: RequestInit) {
        return httpRequest<Response>(url, 'GET', null, config)
    },
    post<Response>(url: string, body: unknown, config?: RequestInit) {
        return httpRequest<Response>(url, 'POST', body, config)
    },
    postForm<Response>(url: string, body: unknown, config?: RequestInit) {
        return httpRequest<Response>(url, 'POST', body, {
            ...config,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        })
    },
    put<Response>(url: string, body: unknown, config?: RequestInit) {
        return httpRequest<Response>(url, 'PUT', body, config)
    },
    patch<Response>(url: string, body: unknown, config?: RequestInit) {
        return httpRequest<Response>(url, 'PATCH', body, config)
    },
    delete<Response>(url: string, body: unknown, config?: RequestInit) {
        return httpRequest<Response>(url, 'DELETE', body, config)
    },
}

export { http }

export default httpRequest
