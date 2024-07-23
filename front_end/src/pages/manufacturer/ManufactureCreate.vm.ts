
import { ManufacturerRequest} from '@/model/Manufacturer'
import { Form, GetProp, UploadFile, UploadProps } from 'antd'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import ManufactureConfigs from './ManufactureConfigs'
import useUploadMultipleImagesApi from '@/hooks/use-upload-multiple-images-api'
import useCreateApi from '@/hooks/use-create-api'
function useManufactureCreateViewModel() {
    const [form] = Form.useForm()
    type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0]
    const [previewOpen, setPreviewOpen] = useState(false)
    const [previewImage, setPreviewImage] = useState('')
    const navigation = useNavigate()
    const [fileList, setFileList] = useState<UploadFile[]>([])
    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 16 },
    }
    const { mutateAsync: createPictures, isPending } = useUploadMultipleImagesApi()
    const { mutate: createManufactureApi } = useCreateApi<ManufacturerRequest, string>(ManufactureConfigs.resourceUrl)

    // INITIAL VALUES
    const initialManufacture : ManufacturerRequest = {
        name: '',
        description: '',
        pictureId: null,
        published: true,
        pageSize: 6,
        priceRangeFiltering: true,
        displayOrder: 0,
    }

    const getBase64 = (file: FileType): Promise<string> =>
        new Promise((resolve, reject) => {
            const reader = new FileReader()
            reader.readAsDataURL(file)
            reader.onload = () => resolve(reader.result as string)
            reader.onerror = (error) => reject(new Error(error.total.toString()))
        })

    const handlePreview = async (file: UploadFile) => {
        if (!file.url && !file.preview) {
            file.preview = await getBase64(file.originFileObj as FileType)
        }
        setPreviewImage(file.url ?? (file.preview as string))
        setPreviewOpen(true)
    }

    const handleChange: UploadProps['onChange'] = ({ fileList: newFileList }) => {
        setFileList(newFileList)
    }

    // HANDLER FUNCTIONS FOR FORM COMPONENTS ADD
    const onFinish = async (values: ManufacturerRequest) => {
        if (fileList.length) {
            try {
                const result = await createPictures([fileList[0].originFileObj as File])
                values.pictureId = result.content[0]
            } catch (error) {
                console.error(error)
            }
        }
        createManufactureApi(values, { onSuccess: () => navigation(-1) })
    }

    return {
        form,
        layout,
        onFinish,
        isPending,
        fileList,
        setFileList,
        handleChange,
        handlePreview,
        previewOpen,
        setPreviewOpen,
        previewImage,
        setPreviewImage,
        initialManufacture,
    }
}

export default useManufactureCreateViewModel