import FetchUtils, { ErrorMessage } from '@/utils/FetchUtils'
import NotifyUtils from '@/utils/NotifyUtils'
import { useMutation, useQueryClient, InvalidateQueryFilters } from '@tanstack/react-query'

function useDeleteByIdsApi<T = number>(resourceUrl: string, resourceKey: string) {
    const queryClient = useQueryClient()

    return useMutation<void, ErrorMessage, T[]>({
        mutationFn: (entityIds) => FetchUtils.deleteByIds(resourceUrl, entityIds),
        onSuccess: () => {
            NotifyUtils.simpleSuccess('Deleted successful')
            queryClient.invalidateQueries([resourceKey, 'getAll'] as InvalidateQueryFilters)
        },
        onError: () => NotifyUtils.simpleFailed('Deletion failed'),
    })
}

export default useDeleteByIdsApi
