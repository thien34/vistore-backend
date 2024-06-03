import { http } from '@/libs/http'

export const auth = {
    login: (data: string) => {
        return http.post<string>('/auth/login', data)
    },
}
