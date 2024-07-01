import { http } from '@/libs/http'
import { PictureRequest, PictureResponseBasic } from '../types/Picture'

class PictureService {
    async uploadPicture(request: PictureRequest) {
        console.log(request.images[0])
        const url = '/admin/picture'
        const formData = new FormData()
        formData.set('images', request.images[0])
        const result = await http.postForm<PictureResponseBasic>(url, formData)
        return result.payload.data
    }
}
export default new PictureService()
