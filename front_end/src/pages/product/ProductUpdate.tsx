import { Table, Button, Collapse, Tabs, Form, Input, Modal, InputNumber } from 'antd'
import { PlusOutlined, ReloadOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import useProductUpdateViewModel from '@/pages/product/ProductUpdate.vm'
import ProductAttributeMappingConfigs from '../product-attribute-mapping/ProductAttributeMappingConfigs'
import ProductAttributeMapping from '../product-attribute-mapping/ProductAttributeMapping'
import ProductAtbCombinationsManage from '../product-attribute-combinations'
import { RuleObject } from 'antd/es/form'
import AppActions from '@/constants/AppActions '
const { Panel } = Collapse
const validateURL = (_: RuleObject, value: string) => {
    const urlPattern = new RegExp(
        '^(https?:\\/\\/)' + // validate the protocol
            '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.?)+[a-z]{2,}|' + // validate the domain name
            '((\\d{1,3}\\.){3}\\d{1,3}))' + // validate OR ip (v4) address
            '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // validate port and path
            '(\\?[;&a-z\\d%_.~+=-]*)?' + // validate query string
            '(\\#[-a-z\\d_]*)?$',
        'i',
    ) // validate fragment locator
    if (!value || urlPattern.test(value)) {
        return Promise.resolve()
    }
    return Promise.reject(new Error('Please enter a valid URL!'))
}

export default function ProductUpdate() {
    const {
        listResponse,
        isLoading,
        filter,
        handleTableChange,
        columns,
        addProductVideoMapping,
        form,
        isEditModalVisible,
        editRecord,
        handleCancelEdit,
        columnsProductVideo,
        isLoadingMultimedia,
        handleUpdateProductVideo,
        listMultimediaResponse,
    } = useProductUpdateViewModel()

    return (
        <>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                <Collapse
                    collapsible='header'
                    defaultActiveKey={['1']}
                    className='mb-6'
                    items={[
                        {
                            key: '1',
                            label: ProductAttributeMappingConfigs.resourceKey,
                            children: (
                                <Tabs
                                    defaultActiveKey='1'
                                    type='card'
                                    size='middle'
                                    items={[
                                        {
                                            key: '1',
                                            label: 'Attributes',
                                            children: <ProductAttributeMapping />,
                                        },
                                        {
                                            key: '2',
                                            label: 'Attribute combinations',
                                            children: <ProductAtbCombinationsManage />,
                                        },
                                    ]}
                                />
                            ),
                        },
                    ]}
                />
                <Collapse defaultActiveKey={['1']} className='mb-6'>
                    <Panel header='Product Specification Attributes' key='1'>
                        <Table
                            dataSource={listResponse?.items || []}
                            columns={columns}
                            pagination={{
                                current: filter.pageNo ?? 1,
                                pageSize: filter.pageSize ?? 6,
                                total: listResponse?.totalPages * (filter.pageSize ?? 6) || 0, // Sử dụng giá trị mặc định nếu listResponse hoặc totalPages không tồn tại
                                onChange: (page, pageSize) => handleTableChange({ current: page, pageSize: pageSize }),
                            }}
                            loading={isLoading}
                            bordered
                        />
                        <Link to={`/admin/product/product-spec-attribute/add/${filter.productId}`}>
                            <Button type='primary' style={{ marginTop: '20px' }}>
                                + Add attribute
                            </Button>
                        </Link>
                        <Button style={{ margin: 10 }} icon={<ReloadOutlined />}></Button>
                    </Panel>
                </Collapse>
                <Collapse defaultActiveKey={['1']} className='mb-6'>
                    <Panel header='Multimedia' key='1'>
                        <Table
                            columns={columnsProductVideo}
                            dataSource={listMultimediaResponse?.items || []}
                            pagination={{
                                current: filter.pageNo ?? 1,
                                pageSize: filter.pageSize ?? 6,
                                total: (listMultimediaResponse?.totalPages ?? 0) * (filter.pageSize ?? 6),
                                onChange: (page, pageSize) => handleTableChange({ current: page, pageSize: pageSize }),
                            }}
                            rowKey='id'
                            loading={isLoadingMultimedia}
                            bordered
                        />

                        <Collapse defaultActiveKey={['1']} className='mb-6'>
                            <Collapse.Panel header='Add New Product Video' key='2'>
                                <div style={{ padding: '0 10%' }}>
                                    <Form
                                        initialValues={{ displayOrder: 0 }}
                                        form={form}
                                        layout='vertical'
                                        onFinish={addProductVideoMapping}
                                        style={{ marginTop: '16px' }}
                                    >
                                        <Form.Item
                                            label='Embed video URL'
                                            name='videoUrl'
                                            rules={[
                                                { required: true, message: 'Please input the video URL!' },
                                                { validator: validateURL },
                                                { max: 100, message: 'The URL cannot be longer than 100 characters!' },
                                            ]}
                                        >
                                            <Input size='large' maxLength={101} />
                                        </Form.Item>

                                        <Form.Item
                                            label='Display order'
                                            name='displayOrder'
                                            tooltip='Set the display order'
                                            rules={[
                                                { required: true, message: 'Please enter the display order!' },
                                                {
                                                    type: 'number',
                                                    min: 0,
                                                    max: 2000000,
                                                    message: 'Display order must be between 0 and 2,000,000!',
                                                },
                                            ]}
                                        >
                                            <InputNumber size='large' type='number' />
                                        </Form.Item>

                                        <Form.Item style={{ textAlign: 'center' }}>
                                            <Button
                                                icon={<PlusOutlined />}
                                                size='large'
                                                className='bg-[#374151] border-[#374151] text-white m-1'
                                                htmlType='submit'
                                            >
                                                Add new video
                                            </Button>
                                        </Form.Item>
                                    </Form>
                                </div>
                            </Collapse.Panel>
                        </Collapse>
                    </Panel>
                </Collapse>
            </div>
            <Modal title='Edit Product Video' open={isEditModalVisible} footer={null} onCancel={handleCancelEdit}>
                {editRecord && (
                    <Form
                        initialValues={{
                            videoUrl: editRecord.videoUrl,
                            displayOrder: editRecord.displayOrder,
                        }}
                        layout='vertical'
                        onFinish={handleUpdateProductVideo}
                    >
                        <Form.Item
                            label='Embed video URL'
                            name='videoUrl'
                            rules={[
                                { required: true, message: 'Please input the video URL!' },
                                { validator: validateURL },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label='Display order'
                            name='displayOrder'
                            rules={[{ required: true, message: 'Please input the display order!' }]}
                        >
                            <Input type='number' />
                        </Form.Item>
                        <Form.Item>
                            <Button type='primary' htmlType='submit'>
                                {AppActions.EDIT}
                            </Button>
                        </Form.Item>
                    </Form>
                )}
            </Modal>
        </>
    )
}
