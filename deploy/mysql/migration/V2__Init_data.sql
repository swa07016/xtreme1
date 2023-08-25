INSERT INTO `x1_community`.`model`(`id`, `name`, `version`, `description`, `scenario`, `dataset_type`, `model_type`, `model_code`, `url`, `is_deleted`, `del_unique_key`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Basic Lidar Object Detection11', 'v0.1.2', '<p>Basic Lidar Object Detection Model empowers you to detect the most common classes precisely and efficiently during lidar and lidar fusion annotation in Autonomous Vehicle Industry.&nbsp;<br><br>• &nbsp;BLOD is a hyper-based methodology invented, trained and maintained by Basic AI&nbsp;<br><br>• &nbsp;BLOD has been testified in more than 20TB production data.&nbsp;<br><br>• &nbsp;BLOD boosts your annotation speed by at least 26%<br><br>• &nbsp;Try it for free now!</p>', '[\"Lidar\",\"Lidar fusion\",\"Autonomous Vehicle\",\"Object Detection\"]', 'LIDAR', 'DETECTION', 'LIDAR_DETECTION', 'http://point-cloud-object-detection:5000/pointCloud/recognition', b'0', 0, current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model`(`id`, `name`, `version`, `description`, `scenario`, `dataset_type`, `model_type`, `model_code`, `url`, `is_deleted`, `del_unique_key`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'COCO Object Detection', 'v0.1.0', '<p>\r\n    The COCO Object Detection Model, trained on MS COCO dataset, empowers you to detect 80 common classes precisely and efficiently. It outputs both bounding boxes and classes.\r\n</p>\r\n<p>\r\n    • &nbsp;It saves your annotation edits by at least 41.3% in 80 COCO classes.\r\n</p>\r\n<p>\r\n    • &nbsp;It is extremely fast. Results can be obtained in a short time.\r\n</p>\r\n<p>\r\n    • &nbsp;It is highly expandable, which allows new models with new datasets and classes to be trained easily. Contact us to know more.\r\n</p>\r\n<p>\r\n    • &nbsp;Try it for FREE now!\r\n</p>', '[\"Common Objects Detection\"]', 'IMAGE', 'DETECTION', 'IMAGE_DETECTION', 'http://image-object-detection:5000/image/recognition ', b'0', 0, current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`user`(`id`,`username`, `password`, `nickname`) VALUES (1,'admin@xtreme1.io', '$2a$10$0qk8vIkREsV6KYPeYJLU..C/JxJZc/ccfZIcEmFcnS8W9DtOD/y5K', 'admin');
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Person', 'person', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Bicycle', 'bicycle', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Car', 'car', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Motorcycle', 'motorcycle', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Airplane', 'airplane', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Train', 'train', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Truck', 'truck', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Boat', 'boat', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Traffic Light', 'traffic light', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Fire Hydrant', 'fire hydrant', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Stop Sign', 'stop sign', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Parking Meter', 'parking meter', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Bench', 'bench', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Bird', 'bird', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Cat', 'cat', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Dog', 'dog', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Horse', 'horse', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Sheep', 'sheep', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Cow', 'cow', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Elephant', 'elephant', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Bear', 'bear', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Zebra', 'zebra', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Giraffe', 'giraffe', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Backpack', 'backpack', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Umbrella', 'umbrella', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Handbag', 'handbag', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Tie', 'tie', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Suitcase', 'suitcase', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Frisbee', 'frisbee', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Skis', 'skis', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Snowboard', 'snowboard', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Sports Ball', 'sports ball', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Kite', 'kite', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Baseball Bat', 'baseball bat', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Baseball Glove', 'baseball glove', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Skateboard', 'skateboard', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Surfboard', 'surfboard', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Tennis Racket', 'tennis racket', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Bottle', 'bottle', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Wine Glass', 'wine glass', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Cup', 'cup', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Fork', 'fork', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Knife', 'knife', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Spoon', 'spoon', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Bowl', 'bowl', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Banana', 'banana', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Apple', 'apple', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Sandwich', 'sandwich', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Orange', 'orange', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Broccoli', 'broccoli', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Carrot', 'carrot', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Hot Dog', 'hot dog', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Pizza', 'pizza', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Donut', 'donut', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Cake', 'cake', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Chair', 'chair', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Couch', 'couch', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Potted Plant', 'potted plant', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Bed', 'bed', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Dining Table', 'dining table', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Toilet', 'toilet', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Tv', 'tv', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Laptop', 'laptop', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Mouse', 'mouse', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Remote', 'remote', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Keyboard', 'keyboard', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Cell Phone', 'cell phone', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Microwave', 'microwave', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Oven', 'oven', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Toaster', 'toaster', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Sink', 'sink', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Refrigerator', 'refrigerator', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Book', 'book', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Clock', 'clock', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Vase', 'vase', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Scissors', 'scissors', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Teddy Bear', 'teddy bear', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Hair Drier', 'hair drier', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Toothbrush', 'toothbrush', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (2, 'Bus', 'bus', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Car', 'CAR', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Truck', 'TRUCK', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Construction Vehicle', 'CONSTRUCTION_VEHICLE ', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, ' Bus', 'BUS', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Trailer', 'TRAILER', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Barrier', 'BARRIER', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Motorcycle', 'MOTORCYCLE', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Bicycle', 'BICYCLE', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Pedestrian', 'PEDESTRIAN', current_timestamp, 1, NULL, NULL);
INSERT INTO `x1_community`.`model_class`(`model_id`, `name`, `code`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES (1, 'Traffic Cone', 'TRAFFIC_CONE', current_timestamp, 1, NULL, NULL);
