import FetchUtils, { ErrorMessage } from '@/utils/FetchUtils'
import NotifyUtils from '@/utils/NotifyUtils'
import { useMutation, useQueryClient, InvalidateQueryFilters } from '@tanstack/react-query'

function useDeleteByIdApi<T = number>(resourceUrl: string, resourceKey: string) {
    const queryClient = useQueryClient()

    return useMutation<void, ErrorMessage, T>({
        mutationFn: (entityId: T) => FetchUtils.deleteById(resourceUrl, entityId),
        onSuccess: () => {
            NotifyUtils.simpleSuccess('Deleted successful')
            queryClient.invalidateQueries([resourceKey, 'getAll'] as InvalidateQueryFilters)
        },
        onError: () => NotifyUtils.simpleFailed('Deletion failed'),
    })
}

export default useDeleteByIdApi
