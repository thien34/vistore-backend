import AppActions from "@/constants/AppActions"
import { SyncOutlined } from "@ant-design/icons"
import { Button } from "antd"
import { useParams } from "react-router-dom"
import RelatedProductConfigs from "./RelatedProductCofigs"

interface RelatedProductSearchProps {
    onSearch: (filter: { productId: string }) => void
    handleDelete: () => void
    selectedRows: React.Key[]
    handleCreate: () => void
}
export default function RelatedProductAction({ onSearch,handleCreate, handleDelete, selectedRows }: Readonly<RelatedProductSearchProps>) {
    const { productId = '1' } = useParams();
    const handleReload = () => {
        onSearch({ productId: productId })
    }
    return (
        <div>
            <div className='flex items-center space-x-4'>
                <Button
                    disabled={selectedRows.length === 0}
                    onClick={handleDelete}
                    type='primary'
                    className='me-5'
                    danger
                    size='large'
                >
                    {AppActions.DELETE}
                </Button>
                <Button className='bg-[#475569] text-white' size='large' onClick={() => handleCreate()}>
                    {RelatedProductConfigs.ADD_RELATED_PRODUCT}
                </Button>
                <Button
                    onClick={handleReload}
                    type='primary'
                    className='bg-[#475569] text-white'
                    size='large'
                >
                    <SyncOutlined />
                </Button>
            </div>
        </div>
    )
}