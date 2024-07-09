import FetchUtils, { ErrorMessage } from '@/utils/FetchUtils'
import { useQuery, UseQueryOptions } from '@tanstack/react-query'

function useGetByIdApi<O>(
    resourceUrl: string,
    resourceKey: string,
    entityId: number,
    successCallback?: (data: O) => void,
    options?: UseQueryOptions<O, ErrorMessage>,
) {
    const queryKey = [resourceKey, 'getById', entityId]

    const query = useQuery<O, ErrorMessage>({
        queryKey: queryKey,
        queryFn: () => FetchUtils.getById<O>(resourceUrl, entityId),
        ...options,
    })

    if (query.isSuccess && query.data) {
        if (successCallback) {
            successCallback(query.data)
        }
    }
    return query
}

export default useGetByIdApi
