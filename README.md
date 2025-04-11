# Image Compression using Quadtree


Tucil2 ini mengimplementasikan algoritma **kompresi gambar berbasis Quadtree** menggunakan bahasa pemrograman Java. Program ini mendukung berbagai metode pengukuran error seperti **Variance**, **MAD**, **MPD**, **Entropy**, dan **SSIM**.

---

## 📌 Apa itu Kompresi Quadtree?

**Kompresi Quadtree** adalah metode kompresi citra yang bekerja dengan membagi citra menjadi empat bagian secara rekursif berdasarkan tingkat keseragaman warna piksel dalam suatu blok. Jika blok tersebut dianggap cukup homogen (berdasarkan nilai error), maka blok tersebut tidak akan dibagi lebih lanjut dan disimpan sebagai node daun (leaf). Jika tidak, maka blok akan dibagi lagi menjadi empat kuadran: kiri atas, kanan atas, kiri bawah, dan kanan bawah.

Dengan pendekatan ini, struktur data yang terbentuk **quadtree**, yang efisien dalam merepresentasikan area gambar yang memiliki kesamaan warna secara visual.

---

## 🗂️ Struktur Proyek

```bash
src/
├── Business/
│   ├── ImageTree.java
│   └── QuadTreeNode.java
├── ErrorMethods/
│   ├── _ErrorMethod.java
│   ├── EntropyErrorMethod.java
│   ├── MADErrorMethod.java
│   ├── MPDErrorMethod.java
│   ├── SSIMErrorMethod.java
│   └── VarianceErrorMethod.java
├── Utils/
│   ├── CLIo.java
│   └── Utils.java
└── Main.java
```

## Cara Menjalankan Program

### ✅ Jalankan ./run.bat

```bash
./run.bat
```

## Requirements

- java

### by: Barru Adi Utomo - 13523101