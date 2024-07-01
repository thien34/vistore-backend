import { PictureRequest } from '../types/Picture'

class PictureService {
    async uploadPicture(request: PictureRequest) {
        const formData = new FormData()
        formData.set('images[0]', request.images[0])
        const result = await fetch('http://localhost:8080/admin/picture', {
            body: formData,
            method: 'POST',
        })
        return result.json()
    }
}
export default new PictureService()
