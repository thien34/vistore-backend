import FetchUtils, { ErrorMessage } from '@/utils/FetchUtils'
import NotifyUtils from '@/utils/NotifyUtils'
import { InvalidateQueryFilters, useMutation, useQueryClient } from '@tanstack/react-query'

function useUpdateApi<I>(resourceUrl: string, resourceKey: string, entityId: number) {
    const queryClient = useQueryClient()

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    return useMutation<any, ErrorMessage, I>({
        mutationFn: (requestBody: I) => FetchUtils.update<I>(resourceUrl, entityId, requestBody),
        onSuccess: (data) => {
            NotifyUtils.simpleSuccess(data.message)
            void queryClient.invalidateQueries([resourceKey, 'getById', entityId] as InvalidateQueryFilters)
            void queryClient.invalidateQueries([resourceKey, 'getAll'] as InvalidateQueryFilters)
        },
        onError: (data) => NotifyUtils.simpleFailed(data.message),
    })
}

export default useUpdateApi
