import { useMutation, useQuery } from '@tanstack/react-query'
import PictureService from '../apis/PictureService'

export const useCreatePictures = () => {
    return useMutation({
        mutationKey: ['createPictures'],
        mutationFn: (picture: File[]) => PictureService.uploadPicture(picture),
    })
}

export const useGetPicture = (id?: number) => {
    return useQuery({
        queryKey: ['getPicture', id],
        queryFn: () => (id ? PictureService.getPicture(id) : null),
    })
}
