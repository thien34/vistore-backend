import { Table, Button, Collapse, Tabs, Form, Input, Modal, InputNumber, Switch, Upload, Empty } from 'antd'
import { PlusOutlined, ReloadOutlined, UploadOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import useProductUpdateViewModel from '@/pages/product/ProductUpdate.vm'
import ProductAttributeMappingConfigs from '../product-attribute-mapping/ProductAttributeMappingConfigs'
import ProductAttributeMapping from '../product-attribute-mapping/ProductAttributeMapping'
import ProductAtbCombinationsManage from '../product-attribute-combinations'
import AppActions from '@/constants/AppActions '

const { Panel } = Collapse
const { TabPane } = Tabs

export default function ProductUpdate() {
    const {
        listResponse,
        isLoading,
        filter,
        handleTableChange,
        isLoadingPicture,
        columnsProductPicture,
        listPictureResponse,
        columns,
        form,
        editForm,
        isEditModalVisible,
        editRecord,
        isEditPicture,
        setIsEditPicture,
        validateURL,
        handleCancelEdit,
        handleUpdateProductPicture,
        columnsProductVideo,
        isLoadingMultimedia,
        handleUpdateProductVideo,
        listMultimediaResponse,
        handleAddUrlVideo,
        fileList,
        isUploadMode,
        uploading,
        propsUploadPicture,
        handleAddUploadPicture,
        propsUpload,
        handleSwitchChange,
        handleAddUploadVideo,
        loading,
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
                        {listResponse && (
                            <Table
                                rowKey='id'
                                dataSource={listResponse?.items}
                                columns={columns}
                                bordered
                                pagination={{
                                    current: filter.pageNo ?? 1,
                                    pageSize: filter.pageSize ?? 6,
                                    total: listResponse.totalPages * (filter.pageSize ?? 6),
                                    onChange: (page, pageSize) =>
                                        handleTableChange({ current: page, pageSize: pageSize }),
                                }}
                                loading={isLoading}
                            />
                        )}

                        <Link to={`/admin/product/product-spec-attribute-add/?productId=${filter.productId}`}>
                            <Button type='primary'>+ Add attribute</Button>
                        </Link>
                        <Button style={{ margin: 10 }} icon={<ReloadOutlined />} />
                    </Panel>
                </Collapse>
                <Collapse defaultActiveKey={['1']} className='mb-6'>
                    <Panel header='Multimedia' key='1'>
                        <Tabs defaultActiveKey='1'>
                            <TabPane tab='Pictures' key='1'>
                                <Table
                                    columns={columnsProductPicture}
                                    dataSource={listPictureResponse?.items}
                                    pagination={{
                                        current: filter.pageNo ?? 1,
                                        pageSize: filter.pageSize ?? 6,
                                        total: (listPictureResponse?.totalPages ?? 0) * (filter.pageSize ?? 6),
                                        onChange: (page, pageSize) =>
                                            handleTableChange({ current: page, pageSize: pageSize }),
                                    }}
                                    scroll={{ x: 900 }}
                                    rowKey='id'
                                    loading={isLoadingPicture}
                                    bordered
                                />
                                <Collapse defaultActiveKey={['1']} className='mb-6'>
                                    <Panel header='Add New Product Picture' key='2'>
                                        <Form
                                            initialValues={{ displayOrder: 0 }}
                                            form={form}
                                            layout='vertical'
                                            onFinish={handleAddUploadPicture}
                                            style={{ marginTop: '16px' }}
                                        >
                                            <Form.Item
                                                label='Upload picture'
                                                name='files'
                                                rules={[{ required: true, message: 'Please upload an image file!' }]}
                                            >
                                                <Upload
                                                    {...propsUploadPicture}
                                                    multiple
                                                    maxCount={5}
                                                    accept='image/*'
                                                    onChange={(info) => {
                                                        if (info.fileList.length > 5) {
                                                            info.fileList = info.fileList.slice(-5)
                                                        }
                                                    }}
                                                    fileList={fileList}
                                                >
                                                    <Button icon={<UploadOutlined />}>Click to Upload</Button>
                                                </Upload>
                                            </Form.Item>
                                            <Form.Item style={{ textAlign: 'center' }}>
                                                <Button
                                                    icon={<PlusOutlined />}
                                                    size='large'
                                                    className='bg-[#374151] border-[#374151] text-white m-1'
                                                    htmlType='submit'
                                                    disabled={uploading}
                                                    loading={uploading}
                                                >
                                                    Upload picture
                                                </Button>
                                            </Form.Item>
                                        </Form>
                                    </Panel>
                                </Collapse>
                                <Modal
                                    title='Edit Picture'
                                    visible={isEditPicture}
                                    onCancel={() => setIsEditPicture(false)}
                                    footer={null}
                                >
                                    <Form form={form} layout='vertical' onFinish={handleUpdateProductPicture}>
                                        <Form.Item
                                            label='Display Order'
                                            name='displayOrder'
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
                                            <InputNumber size='large' />
                                        </Form.Item>
                                        <Form.Item style={{ textAlign: 'center' }}>
                                            <Button
                                                type='primary'
                                                htmlType='submit'
                                                className='bg-[#374151] border-[#374151] text-white'
                                                loading={loading}
                                            >
                                                Save
                                            </Button>
                                        </Form.Item>
                                    </Form>
                                </Modal>
                            </TabPane>
                            <TabPane tab='Videos' key='2'>
                                {listMultimediaResponse?.items?.length ? (
                                    <Table
                                        columns={columnsProductVideo}
                                        dataSource={listMultimediaResponse.items}
                                        pagination={{
                                            current: filter.pageNo ?? 1,
                                            pageSize: filter.pageSize ?? 6,
                                            total: (listMultimediaResponse?.totalPages ?? 0) * (filter.pageSize ?? 6),
                                            onChange: (page, pageSize) =>
                                                handleTableChange({ current: page, pageSize: pageSize }),
                                        }}
                                        scroll={{ x: 900 }}
                                        rowKey='id'
                                        loading={isLoadingMultimedia}
                                        bordered
                                    />
                                ) : (
                                    <div
                                        style={{
                                            display: 'flex',
                                            justifyContent: 'center',
                                        }}
                                    >
                                        <Empty
                                            image='https://gw.alipayobjects.com/zos/antfincdn/ZHrcdLPrvN/empty.svg'
                                            imageStyle={{ height: 60 }}
                                        />
                                    </div>
                                )}
                                <Collapse defaultActiveKey={['1']} className='mb-6'>
                                    <Panel header='Add New Product Video' key='2'>
                                        <div style={{ marginBottom: '16px' }}>
                                            <Switch
                                                checked={isUploadMode}
                                                onChange={handleSwitchChange}
                                                checkedChildren='Upload video'
                                                unCheckedChildren='Embed video URL'
                                            />
                                        </div>
                                        {isUploadMode ? (
                                            <Form
                                                initialValues={{ displayOrder: 0 }}
                                                form={form}
                                                layout='vertical'
                                                onFinish={handleAddUploadVideo}
                                                style={{ marginTop: '16px' }}
                                            >
                                                <Form.Item
                                                    label='Upload video'
                                                    name='videoFile'
                                                    rules={[{ required: true, message: 'Please upload a video file!' }]}
                                                >
                                                    <Upload {...propsUpload} maxCount={1} accept='video/*'>
                                                        <Button icon={<UploadOutlined />}>Click to Upload</Button>
                                                    </Upload>
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
                                                        disabled={uploading}
                                                        loading={uploading}
                                                    >
                                                        Upload video
                                                    </Button>
                                                </Form.Item>
                                            </Form>
                                        ) : (
                                            <Form
                                                initialValues={{ displayOrder: 0 }}
                                                form={form}
                                                layout='vertical'
                                                onFinish={handleAddUrlVideo}
                                                style={{ marginTop: '16px' }}
                                            >
                                                <Form.Item
                                                    label='Embed video URL'
                                                    name='videoUrl'
                                                    rules={[
                                                        { required: true, message: 'Please input the video URL!' },
                                                        { validator: validateURL },
                                                        {
                                                            max: 100,
                                                            message: 'The URL cannot be longer than 100 characters!',
                                                        },
                                                    ]}
                                                >
                                                    <Input size='large' maxLength={100} />
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
                                                        Add video URL
                                                    </Button>
                                                </Form.Item>
                                            </Form>
                                        )}
                                    </Panel>
                                </Collapse>
                            </TabPane>
                        </Tabs>
                    </Panel>
                </Collapse>
            </div>
            <Modal title='Edit Product Video' open={isEditModalVisible} footer={null} onCancel={handleCancelEdit}>
                {editRecord && (
                    <Form
                        form={editForm}
                        initialValues={editRecord}
                        layout='vertical'
                        onFinish={handleUpdateProductVideo}
                    >
                        <Form.Item>
                            <Switch
                                checked={isUploadMode}
                                onChange={handleSwitchChange}
                                checkedChildren='Upload video'
                                unCheckedChildren='Embed video URL'
                                disabled={!isUploadMode}
                            />
                        </Form.Item>

                        {isUploadMode ? (
                            <Form.Item
                                label='Upload video'
                                name='videoFile'
                                rules={[{ required: true, message: 'Please upload a video file!' }]}
                            >
                                <Upload {...propsUpload} maxCount={1} accept='video/*' fileList={fileList}>
                                    <Button icon={<UploadOutlined />}>Click to Upload</Button>
                                </Upload>
                            </Form.Item>
                        ) : (
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
                        )}

                        <Form.Item
                            label='Display order'
                            name='displayOrder'
                            rules={[{ required: true, message: 'Please input the display order!' }]}
                        >
                            <Input type='number' />
                        </Form.Item>
                        <Form.Item>
                            <Button loading={loading} type='primary' htmlType='submit'>
                                {AppActions.EDIT}
                            </Button>
                        </Form.Item>
                    </Form>
                )}
            </Modal>
        </>
    )
}
