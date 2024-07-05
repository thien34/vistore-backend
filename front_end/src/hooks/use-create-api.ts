import FetchUtils, { ErrorMessage } from '@/utils/FetchUtils'
import NotifyUtils from '@/utils/NotifyUtils'
import { useMutation } from '@tanstack/react-query'

function useCreateApi<I, O>(resourceUrl: string) {
    return useMutation<O, ErrorMessage, I>({
        mutationFn: (requestBody: I) => FetchUtils.create<I, O>(resourceUrl, requestBody),
        onSuccess: () => NotifyUtils.simpleSuccess('Create successful'),
        onError: () => NotifyUtils.simpleFailed('Create failed'),
    })
}

export default useCreateApi
