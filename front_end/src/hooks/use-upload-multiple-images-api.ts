import { CollectionWrapper } from '@/types/CollectionWrapper'
import FetchUtils, { ErrorMessage } from '@/utils/FetchUtils'
import NotifyUtils from '@/utils/NotifyUtils'
import { useMutation } from '@tanstack/react-query'

function useUploadMultipleImagesApi() {
    return useMutation<CollectionWrapper<number>, ErrorMessage, File[]>({
        mutationFn: (images) => FetchUtils.uploadMultipleImages(images),
        onSuccess: () => {
            NotifyUtils.simpleSuccess('Create successful')
        },
        onError: () => NotifyUtils.simpleFailed('Create failed'),
    })
}

export default useUploadMultipleImagesApi
