import { http } from '@/libs/http'
import { PictureResponseBase } from '@/model/Picture'

class PictureService {
    async uploadPicture(request: File[]) {
        const formData = new FormData()
        request.forEach((file) => {
            formData.append('images', file)
        })
        const result = await fetch('http://localhost:8080/admin/picture', {
            body: formData,
            method: 'POST',
        })
        return result.json()
    }

    async getPicture(id: number) {
        const url = `/admin/picture/${id}`
        const result = await http.get<PictureResponseBase>(url)
        return result.payload.data
    }
}
export default new PictureService()
