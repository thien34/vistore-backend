import { http } from '@/lib/http'

const auth = {
    login: (data) => {
        return http.post('/auth/login', data)
    }
}

export default auth
