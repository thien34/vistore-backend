import FetchUtils, { ErrorMessage } from '@/utils/FetchUtils'
import NotifyUtils from '@/utils/NotifyUtils'
import { InvalidateQueryFilters, useMutation, useQueryClient } from '@tanstack/react-query'

function useUpdateApi<I, O>(resourceUrl: string, resourceKey: string, entityId: number) {
    const queryClient = useQueryClient()

    return useMutation<O, ErrorMessage, I>({
        mutationFn: (requestBody: I) => FetchUtils.update<I, O>(resourceUrl, entityId, requestBody),
        onSuccess: () => {
            NotifyUtils.simpleSuccess('Create successful')
            void queryClient.invalidateQueries([resourceKey, 'getById', entityId] as InvalidateQueryFilters)
            void queryClient.invalidateQueries([resourceKey, 'getAll'] as InvalidateQueryFilters)
        },
        onError: () => NotifyUtils.simpleFailed('Create failed'),
    })
}

export default useUpdateApi
