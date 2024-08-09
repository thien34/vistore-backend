import FetchUtils, { BasicRequestParams, ErrorMessage } from '@/utils/FetchUtils'
import NotifyUtils from '@/utils/NotifyUtils'
import { useQuery, UseQueryOptions } from '@tanstack/react-query'

function useGetApi<O, P extends BasicRequestParams = BasicRequestParams>(
    resourceUrl: string,
    resourceKey: string,
    requestParams?: P,
    successCallback?: (data: O) => void,
    options?: UseQueryOptions<O, ErrorMessage>,
) {
    const queryKey = [resourceKey, 'get', requestParams]

    const query = useQuery<O, ErrorMessage>({
        queryKey: queryKey,
        queryFn: () => FetchUtils.get<O>(resourceUrl, requestParams),
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

export default useGetApi
