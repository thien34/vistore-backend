import { Button } from 'antd'
import { SaveOutlined, EditOutlined, CopyOutlined, DeleteOutlined } from '@ant-design/icons'
import useProductCreateViewModel from './ProductCreate.vm'
import useProductUpdateViewModel from './ProductUpdate.vm'
type Props = {
    isUpdate: boolean
}

function ProductAction({ isUpdate }: Props) {
    const { handleCreate, handleSaveAndContinueEdit } = useProductCreateViewModel()
    const { handleDeleteProduct } = useProductUpdateViewModel()

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-20'>
            <div className='flex justify-end space-x-2'>
                <Button onClick={handleCreate} size='large' type='primary' icon={<SaveOutlined />}>
                    Save
                </Button>
                <Button onClick={handleSaveAndContinueEdit} size='large' type='primary' icon={<EditOutlined />}>
                    Save and Continue Edit
                </Button>
                {isUpdate && (
                    <>
                        <Button size='large' type='primary' className='bg-green-500' icon={<CopyOutlined />}>
                            Copy Product
                        </Button>
                        <Button
                            onClick={handleDeleteProduct}
                            size='large'
                            danger
                            type='primary'
                            icon={<DeleteOutlined />}
                        >
                            Delete
                        </Button>
                    </>
                )}
            </div>
        </div>
    )
}

export default ProductAction
