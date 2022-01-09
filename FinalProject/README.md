<h1 align="center">Image Filtering</h1>
<h3 align="center"> - Gaussian Blur and Grayscale - </h3>

Team members:\
Maerean Andrei-Serban\
Kokovics Razvan-Florin

## Goal

Applying a simple filter on an image. In our case we choose 2 types of filters: Gaussian Blur and Grayscale.

## Requirement

Each project will have 2 implementations: one with "regular" threads or tasks/futures, and one distributed (possibly, but not required, using MPI). A third implementation, using OpenCL or CUDA, can be made for a bonus.

## Computer Specification

* CPU: Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz   2.21 GHz
* RAM: 16 GB

## Algorithms

### Gaussian blur

In convolution, two mathematical functions are combined to produce a third function. In image processing functions are usually called kernels. A kernel is nothing more than a (square) array of pixels (a small image so to speak). Usually, the values in the kernel add up to one. This is to make sure no energy is added or removed from the image after the operation.

Specifically, a Gaussian kernel (used for Gaussian blur) is a square array of pixels where the pixel values correspond to the values of a Gaussian curve (in 2D).

In our case the Gaussian kernel is the average of a matrix of size depending on the deep of the blur, having in the center our corresponding pixel.

### Grayscale

One approach would be to use the average method which is the most simple one. We just have to take the average of three colours. Since it's an RGB image, so it means that we have to add r and g and b then divide it by 3 to get the desired grayscale image.

    Grayscale = (R + G + B) / 3

#### Problem

Since red colour has more wavelength of all the three colours, and green is the colour that has not only less wavelength than red colour but also green is the colour that gives more soothing effects to the eyes.

It means that we have to decrease the contribution of red colour, and increase the contribution of the green colour, and put the blue colour contribution in between these two.
So the new equation that form is:

    Grayscale = 0.299 * R + 0.587 * G + 0.114 * B

According to this equation, Red has contribute 29.9\%, Green has contributed 58.7\% which is greater in all three colours and Blue has contributed 11.4\%.


## Short Description of the Implementation:

* Threads
* Distributed - MPI
* OpenCL / CUDA


### Threads

We used a thread pool of size 10 and each thread gets a line from the matrix in order to perform the corresponding algorithm. At the end, we wait until all threads finish their task.

### Distributed - MPI

### OpenCL / CUDA


## Performance Tests

Algorithm | Grayscale | Gaussian Blur 10 |  Gaussian Blur 25 | Gaussian Blur 50 |
:---:|:---:|:---:|:---:|:---:
Threads 640x424       | 0.008 s | 0.152 s | 0.44 s  | 1.152 s
Threads 1920x1274     | 0.008 s | 0.712 s | 3.904 s | 14.816 s
Threads 2400x1592     | 0.008 s | 1.088 s | 6.184 s | 23.456 s
Distributed MPI       | ? s     | ? s     | ? s     | ? s
OpenCL / CUDA         | ? s     | ? s     | ? s     | ? s