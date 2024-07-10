import FetchUtils, { ErrorMessage, ListResponse, RequestParams } from '@/utils/FetchUtils'
import NotifyUtils from '@/utils/NotifyUtils'
import { UseQueryOptions, useQuery } from '@tanstack/react-query'

function useGetAllApi<O, P extends RequestParams = RequestParams>(
    resourceUrl: string,
    resourceKey: string,
    requestParams?: P,
    successCallback?: (data: ListResponse<O>) => void,
    options?: UseQueryOptions<ListResponse<O>, ErrorMessage>,
) {
    const queryKey = [resourceKey, 'getAll', requestParams]

    const query = useQuery<ListResponse<O>, ErrorMessage>({
        queryKey: queryKey,
        queryFn: () => FetchUtils.getAll<O>(resourceUrl, requestParams),
        ...options,
    })

    if (query.isSuccess && query.data) {
        if (successCallback) {
            successCallback(query.data)
        }
    }

    if (query.error) {
        NotifyUtils.simpleFailed('Failed to fetch data')
    }

    return query
}

export default useGetAllApi
