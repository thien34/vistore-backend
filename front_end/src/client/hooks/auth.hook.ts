import { useMutation } from '@tanstack/react-query'
import { auth } from '@client/apis/auth'

const useLogin = () => {
    const loginRequest = useMutation({
        mutationFn: (data: string) => auth.login(data),
    })
    return loginRequest
}

export { useLogin }
