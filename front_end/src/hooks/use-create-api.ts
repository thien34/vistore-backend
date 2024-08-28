import FetchUtils, { ErrorMessage } from '@/utils/FetchUtils'
import NotifyUtils from '@/utils/NotifyUtils'
import { useMutation } from '@tanstack/react-query'

function useCreateApi<I>(resourceUrl: string) {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    return useMutation<any, ErrorMessage, I>({
        mutationFn: (requestBody: I) => FetchUtils.create<I>(resourceUrl, requestBody),
        onSuccess: (data) => NotifyUtils.simpleSuccess(data.message),
        onError: (data) => NotifyUtils.simpleFailed(data.message),
    })
}

export default useCreateApi
