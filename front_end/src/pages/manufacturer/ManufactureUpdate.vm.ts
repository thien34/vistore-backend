import useUpdateApi from "@/hooks/use-update-api"
import { ManufacturerRequest, ManufacturerResponse } from "@/model/Manufacturer"
import { GetProp, UploadFile, UploadProps } from "antd"
import { useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import ManufactureConfigs from "./ManufactureConfigs"
import useGetByIdApi from "@/hooks/use-get-by-id-api"
import { PictureResponse } from "@/model/Picture"
import PictureConfigs from "../picture/PictureConfigs"
import useUploadMultipleImagesApi from "@/hooks/use-upload-multiple-images-api"

export default function useManufactureUpdateViewModel ()  {
    const navigation = useNavigate()
    type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0]
    const [fileList, setFileList] = useState<UploadFile[]>([])
    const [previewOpen, setPreviewOpen] = useState(false)
    const [previewImage, setPreviewImage] = useState('')

    const { id } = useParams<{ id: string }>()

    const { mutate: updateManufacturer, isPending } = useUpdateApi<ManufacturerRequest, string>(
        ManufactureConfigs.resourceUrl,
        ManufactureConfigs.resourceKey,
        Number(id),
    )
    const { data: manufactureResponse, isLoading } = useGetByIdApi<ManufacturerResponse>(
        ManufactureConfigs.resourceUrl,
        ManufactureConfigs.resourceKey,
        Number(id),
    )
    const { data: pictureResponse } = useGetByIdApi<PictureResponse>(
        PictureConfigs.resourceUrl,
        PictureConfigs.resourceKey,
        manufactureResponse?.pictureId ?? 0,
    )
    const { mutateAsync: createPictures } = useUploadMultipleImagesApi()

    //
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

    //
    const handleChange: UploadProps['onChange'] = ({ fileList: newFileList }) => {
        setFileList(newFileList)
        console.log(newFileList)
    }

    // HANDLER FUNCTION ON FINISH FORM
    const onFinish = async (values: ManufacturerRequest) => {
        try {
            const result = await createPictures([fileList[0].originFileObj as File])
            values.pictureId = result.content[0]
        } catch (error) {
            console.error(error)
        }
        updateManufacturer({ id: Number(id), ...values }, { onSuccess: () => navigation(-1) })
    }
    return {
        pictureResponse,
        onFinish,
        manufactureResponse,
        isPending,
        isLoading,
        handlePreview,
        handleChange,
        fileList,
        setFileList,
        previewOpen,
        setPreviewOpen,
        previewImage,
        setPreviewImage,
    }
}