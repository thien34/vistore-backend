import { useMutation } from '@tanstack/react-query'
import { PictureRequest } from '../types/Picture'
import PictureService from '../apis/PictureService'

export const useCreatePictures = () => {
    return useMutation({
        mutationKey: ['createPictures'],
        mutationFn: (picture: PictureRequest) => PictureService.uploadPicture(picture),
    })
}
