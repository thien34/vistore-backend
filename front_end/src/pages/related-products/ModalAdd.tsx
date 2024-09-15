import { Modal, Button, Table, Space, Form } from 'antd'
import { Dispatch, SetStateAction,  useEffect,  useState} from 'react'
import useProductManageViewModel from '../product/ProductManage.vm'
import { RelatedProductRequest } from '@/model/RelatedProduct'
import { TableRowSelection } from 'antd/es/table/interface'
import { ProductResponse } from '@/model/Product'
type Props = {
    isModalAddOpen: boolean
    setIsModalAddOpen: Dispatch<SetStateAction<boolean>>
    handleCreateFromModal: (relatedProducts: RelatedProductRequest[]) => void
    title: string
    setTitle: (title: string) => void
    productId: number
}
const layout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 18 },
}
export default function ModalAdd({
    isModalAddOpen,
    setIsModalAddOpen,
    title,
    setTitle,
    productId,
    handleCreateFromModal
  }: Readonly<Props>) {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
    const [form] = Form.useForm()
    const { dataSource, columns } = useProductManageViewModel()
    const [ lstRelatedProduct, setLstRelatedProduct ]= useState<RelatedProductRequest[]>([]);
    const handleAddProduct = () => {
        const selectedRowKey = rowSelections.selectedRowKeys as number[];
        const updatedProducts = selectedRowKey.map((key: React.Key) => ({
          product1Id: parseInt(productId?.toString() ?? "1"),
          product2Id: key as number,
          displayOrder: 1,
        } as RelatedProductRequest));
        setLstRelatedProduct(updatedProducts);
    };
    useEffect(() => {
      if (selectedRowKeys.length > 0) {
        handleAddProduct();
      } else {
        setLstRelatedProduct([]);
      }
    }, [selectedRowKeys]);
      const handleCloseModal = () => {
        setTitle('Add Related Product');
        setIsModalAddOpen(false);
      };
      const rowSelections: TableRowSelection<ProductResponse> = {
        selectedRowKeys,
        onChange: (selectedRowKeys) => {
          setSelectedRowKeys(selectedRowKeys);
        },
      };
      const onFinish = () => {
        handleAddProduct();
        handleCreateFromModal(lstRelatedProduct); 
        handleCloseModal();
      };
    return (
        <Modal closable={true} title={title}  open={isModalAddOpen} onCancel={handleCloseModal} footer={null}>
            <Form form ={form} {...layout}  className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40' name='control-related-product-add' onFinish={onFinish}>
                <Table
                    rowKey='id'
                    bordered
                    rowSelection={rowSelections}
                    dataSource={dataSource}
                    columns={columns}
                    pagination={{ pageSize: 6 }}
                ></Table>
                <Space>
                        <Button type='primary' htmlType='submit'>
                            Save
                        </Button>
                        <Button htmlType='button' onClick={handleCloseModal}>
                            Cancel
                        </Button>
                    </Space>
                </Form>
        </Modal>
    )
}